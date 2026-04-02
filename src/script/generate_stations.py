#!/usr/bin/env python

import os
import sys
import csv
import json

ID = 0
NAME = 1

def exit_help(error_msg: str = None):
    status_code = 0

    if error_msg:
        print(f"{error_msg}\n", file=sys.stderr)
        status_code = 1

    print("USAGE: generate_stations.py CSV_STATION_FILE\n")
    print("CSV file should be formatted as below.", end=' ')
    print("The first row is assumed to be the column names.")
    print("    | id | name       |")
    print("    -------------------")
    print("    |  1 | Abbesses   |")
    print("    |  2 | Aéroport   |")
    print("    | .......         |\n")
    print("You can use a query similar to\n")
    print("  > SELECT id, name FROM station;\n")
    print("and export as CSV.")

    sys.exit(status_code)


def read_csv(filename: str) -> list:
    try:
        with open(filename, newline='', encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile)
            return [line for line in reader]
    except IOError as e:
        sys.exit(f"Could not open file '{filename}': {str(e)}")


def csv_to_json_data(csvlines: list) -> list:
    station_data = []

    for line in csvlines[1:]:
        try:
            station_id = int(line[ID])
        except ValueError:
            exit_help(error_msg=f"Value '{line[ID]}' in first column "
                      "not formatted as an integer ID.")

        station_name = line[NAME].strip()
        if station_name == '':
            exit_help(error_msg=f"Empty name for id {station_id}.")

        entry = {
            "id": station_id,
            "name": station_name,
            "tags": [],
        }

        station_data.append(entry)

    return station_data


def write_to_js_file(json_data: dict):
    json_text: str = json.dumps(json_data, indent=4, ensure_ascii=False)

    try:
        with open("../main/resources/static/js/const/stations.js", "w", encoding='utf-8') as f:
            f.write(f"export const STATIONS = {json_text.replace('\n ', '\n   ')};")
    except IOError as e:
        sys.exit(f"Failed to write final JS file: {str(e)}")


def main(argv):
    if len(argv) < 2:
        exit_help(error_msg="Missing CSV filename.")

    if "--help" in argv:
        exit_help()

    csvfile = read_csv(filename=argv[1])

    print(f"Converting file {argv[1]}...") 
    start_wd = os.getcwd()
    os.chdir(os.path.dirname(os.path.abspath(__file__)))

    station_data = csv_to_json_data(csvfile)

    write_to_js_file(station_data)

    os.chdir(start_wd)


if __name__ == "__main__":
    main(sys.argv)
