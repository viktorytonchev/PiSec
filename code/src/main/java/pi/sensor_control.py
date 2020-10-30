from threading import Thread, Lock
from time import sleep

import RPi.GPIO as GPIO
import psycopg2 as db

from pi import security

armed_state_lock = Lock()
SYSTEM_STATE = None

sensor_state_lock = Lock()
SENSOR_STATE = False


def system_state_setter(state):
    global SYSTEM_STATE
    armed_state_lock.acquire()
    try:
        SYSTEM_STATE = state
    finally:
        armed_state_lock.release()
        check_alarm_state()


def system_state_getter():
    global SYSTEM_STATE
    armed_state_lock.acquire()
    try:
        return SYSTEM_STATE
    finally:
        armed_state_lock.release()


def sensor_state_setter(state):
    global SENSOR_STATE
    sensor_state_lock.acquire()
    try:
        SENSOR_STATE = state
    finally:
        sensor_state_lock.release()
        check_alarm_state()


def sensor_state_getter():
    global SENSOR_STATE
    sensor_state_lock.acquire()
    try:
        return SENSOR_STATE
    finally:
        sensor_state_lock.release()


def update_system_state():
    while True:
        sleep(0.01)
        conn = None
        try:
            conn = db.connect(
                host=security.db_host,
                database=security.db_database,
                user=security.db_user,
                password=security.db_password)
            cur = conn.cursor()
            cur.execute("SELECT s.armed FROM dab_di19202b_226.pisec.system s")
            row = cur.fetchone()
            armed = row[0]
            system_state_setter(armed)
        except (Exception, db.DatabaseError) as error:
            print(error)
        finally:
            if conn is not None:
                conn.close()


def system_state_controller():
    thread = Thread(target=update_system_state)
    thread.start()


def initiate_alarm():
    print("alarm initialized")
    conn = None
    try:
        conn = db.connect(
            host=security.db_host,
            database=security.db_database,
            user=security.db_user,
            password=security.db_password)
        cur = conn.cursor()
        cur.execute("UPDATE dab_di19202b_226.pisec.system SET alarm = TRUE WHERE sid = 0")
        conn.commit()

    except (Exception, db.DatabaseError) as error:
        print(error)
    finally:
        if conn is not None:
            conn.close()
    # todo tested, works correctly
    return


def check_alarm_state():
    if sensor_state_getter() and system_state_getter():
        initiate_alarm()


def main():
    # initialize sensor
    GPIO.setmode(GPIO.BCM)
    sensor_gpio = 3
    GPIO.setup(sensor_gpio, GPIO.IN)
    current = GPIO.input(sensor_gpio)

    # initialize system
    state_controller = Thread(target=system_state_controller)
    state_controller.start()

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

        sensor_state_setter(current == 0)


def update_sensor_status(arg):
    conn = None
    try:
        conn = db.connect(
            host=security.db_host,
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
