from threading import Thread, Lock
from time import sleep

import RPi.GPIO as GPIO
import psycopg2 as db

from pi import security

SYSTEM_STATE = None
state_lock = Lock()


def get_system_state():
    conn = None
    try:
        conn = db.connect(
            host=security.db_user,
            database=security.db_database,
            user=security.db_user,
            password=security.db_password)
        cur = conn.cursor()
        cur.execute("SELECT s.armed FROM dab_di19202b_226.pisec.system s")  # where sid = ...
        record = cur.fetchone()
        for row in record:
            armed = row[0]
            return armed
    except (Exception, db.DatabaseError) as error:
        print(error)
    finally:
        if conn is not None:
            conn.close()


def system_state_controller(arg):
    while True:
        sleep(0.01)
        state_lock.acquire()
        try:
            SYSTEM_STATE = get_system_state()
        finally:
            state_lock.release()


def main():
    GPIO.setmode(GPIO.BCM)

    sensor_gpio = 3

    GPIO.setup(sensor_gpio, GPIO.IN)

    current = GPIO.input(sensor_gpio)

    while True:
        sleep(0.01)
        state = GPIO.input(sensor_gpio)
        if state and not current:
            thread = Thread(target=update_sensor_status, args=(True,))
            thread.start()
            current = True
        elif not state and current:
            thread = Thread(target=update_sensor_status, args=(False,))
            thread.start()
            current = False


def update_sensor_status(arg):
    conn = None
    try:
        conn = db.connect(
            host=security.db_user,
            database=security.db_database,
            user=security.db_user,
            password=security.db_password)
        cur = conn.cursor()

        if arg:
            print("setting open = FALSE")
            cur.execute("UPDATE dab_di19202b_226.pisec.sensor SET open = FALSE")
            conn.commit()
        else:
            print("setting open = TRUE")
            cur.execute("UPDATE dab_di19202b_226.pisec.sensor SET open = TRUE")
            conn.commit()

    except (Exception, db.DatabaseError) as error:
        print(error)
    finally:
        if conn is not None:
            conn.close()


if __name__ == '__main__':
    main()
