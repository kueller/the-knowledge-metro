#!/usr/bin/env python

import os
import sys
import csv
import json

LINE_ID = 0
LINE_NAME = 1
TERMINUS_ID = 2
TERMINUS_NAME = 3

def exit_help(error_msg: str = None):
    status_code = 0

    if error_msg:
        print(f"{error_msg}\n", file=sys.stderr)
        status_code = 1

    print("USAGE: generate_line_data.py CSV_FILE\n")
    print("CSV file should be formatted as below.", end=' ')
    print("The first row is assumed to be the column names.", end=' ')
    print("The station ids and names are for the termini.")
    print("    | id | name | sation_id | name      |")
    print("    -------------------------------------")
    print("    |  3 | 3    | 108       | Gallieni  |")
    print("    |  3 | 3    | 154       | Levallois |")
    print("    |  4 | 3bis | 205       | Gambetta  |")
    print("    | .......                           |\n")
    print("You can use a query similar to\n")
    print("  > SELECT ln.id, ln.name, t.station_id, s.name FROM line ln")
    print("    LEFT JOIN terminus t on t.line_id = ln.id")
    print("    LEFT JOIN station s on t.station_id = s.id")
    print("    ORDER BY ln.id;\n")
    print("and export as CSV.\n")
    print("This will result in repeated line values per.", end=' ')
    print("terminus. The script will handle it.")
    print("Output will be an array with the array indices", end=' ')
    print("corresponding to the line IDs.")

    sys.exit(status_code)


def parse_int(field: str, row: int) -> int:
    try:
        return int(field)
    except ValueError:
        exit_help(error_msg=f"Value '{field}' in row {row} "
                  "not formatted as an integer ID.")


def read_csv(filename: str) -> list:
    try:
        with open(filename, newline='', encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile)
            return [line for line in reader]
    except IOError as e:
        sys.exit(f"Could not open file '{filename}': {str(e)}")


def csv_to_json_data(csvlines: list) -> list:
    line_data = {} 

    for i in range(1, len(csvlines)):
        row = csvlines[i]

        line_id = parse_int(row[LINE_ID], i)
        terminus_id = parse_int(row[TERMINUS_ID], i)

        line_name = row[LINE_NAME].strip()
        if line_name == '':
            exit_help(error_msg=f"Empty name for line id {line_id}.")

        terminus_name = row[TERMINUS_NAME].strip()
        if terminus_name == '':
            exit_help(error_msg=f"Empty name for terminus id {terminus_id}.")

        if line_id not in line_data:
            line_data[line_id] = {
                "id": line_id,
                "name": line_name,
                "termini": [],
            }

        line_data[line_id]["termini"].append(
            {
                "id": terminus_id,
                "name": terminus_name,
            }
        )

    line_array = []

    sorted_line_ids = sorted(list(line_data.keys()))
    for i in range(0, sorted_line_ids[-1] + 1):
        if i in line_data:
            line_array.append(line_data[i])
        else:
            line_array.append({})

    return line_array


def write_to_js_file(json_data: dict):
    json_text: str = json.dumps(json_data, indent=4, ensure_ascii=False)

    try:
        with open("../main/resources/static/js/const/lines.js", "w", encoding='utf-8') as f:
            f.write(f"export const LINES = {json_text.replace('\n ', '\n   ')};")
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
