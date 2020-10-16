from threading import Thread
from time import sleep

import RPi.GPIO as GPIO
import psycopg2 as db


def main():
    GPIO.setmode(GPIO.BCM)

    SENSOR_GPIO = 3

    GPIO.setup(SENSOR_GPIO, GPIO.IN)

    current = GPIO.input(SENSOR_GPIO)

    while True:
        sleep(0.01)
        state = GPIO.input(SENSOR_GPIO)
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
            host="bronto.ewi.utwente.nl",
            database="dab_di19202b_226",
            user="dab_di19202b_226",
            password="ivanekuche")
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
