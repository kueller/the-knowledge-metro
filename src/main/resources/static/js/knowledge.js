import { EN_TEXT } from "./locale/en.js";
import { LINES } from "./const/lines.js";
import { STATIONS } from "./const/stations.js";

import Fuse from "https://cdn.jsdelivr.net/npm/fuse.js@7.1.0/dist/fuse.mjs";


const State = {
    LINE: 0,
    DIRECTION: 1,
    STATION: 2,
    FAILURE: 3,
    SUCCESS: 4,
}


const Color = {
    GREEN: "#00b59b",
    RED: "#ec1e2b",
    DARK_GRAY: "#7e8285"
}

const LINES_IN_ORDER = [1, 2, 3, 21, 4, 5, 6, 7, 22, 8, 9, 10, 11, 12, 13, 14];


const TEXT_REPLACEMENTS = {};


const FUSE_OPTIONS = {
    ignoreCase: true,
    ignoreDiacritics: true,
    includeScore: true,
    minMatchCharLength: 4,
    threshold: 0.3,
    distance: 4,
    fieldNormWeight: 2.0,
    keys: [
        {
            name: "name",
            getFn: (station) => { return cleanInput(station.name); }
        },
        "tags"
    ]
};

const fuse = new Fuse(STATIONS, FUSE_OPTIONS);


class KnowledgeGameConfig {
    text = EN_TEXT;

    state = State.LINE;

    origin_id = 0;
    destination_id = 0;
    final_id = 0;
    direction_id = 0;
    line_id = 0;

    current_step = null;
    prev_step = null;
    search_box = null;

}

const GameConfig = new KnowledgeGameConfig();



function set_language() {
    let lang = document.documentElement.lang;

    if (lang == "fr") {
    } else {
        GameConfig.text = EN_TEXT;
    }

    for (let game_text in GameConfig.text) {
        let text_elements = document.getElementsByClassName(game_text);
        for (let text_element of text_elements) {
            text_element.textContent = GameConfig.text[game_text].translation;
            replaceI18NText(text_element, TEXT_REPLACEMENTS[game_text]);
        }
    }
}


function init() {
    set_language();

    let start_dest = document.getElementById("start-dest");
    if (!start_dest.children[0].id.startsWith("origin"))
        return;

    GameConfig.origin_id = Number(start_dest.children[0].id.split("-")[1]);
    GameConfig.final_id = Number(start_dest.children[1].id.split("-")[1]);

    let metro_buttons = document.getElementsByClassName("metro-button");
    for (let i = 0; i < metro_buttons.length; i++) {
        let line_id = metro_buttons[i].id;

        metro_buttons[i].addEventListener(
            "click",
            function (e) { event_metroLineClicked(e, line_id); }
        );
    }

    GameConfig.current_step = document.getElementById("line-select");
    GameConfig.state = State.LINE;
}


function cleanInput(input) {
    return input.trim()
        .replaceAll('-', ' ')
        .replaceAll("'", ' ')
        .replaceAll('’', ' ')
        .split(/\s+/)
        .join(' ');
}




function setSearchBoxToFinalColor(search_box, color) {
    search_box.style.borderColor = color;
    search_box.style.boxShadow = "0px 0px 4px 2px " + color;
}


function replaceI18NText(text_element, replacement_pairs) {
    if (replacement_pairs == null || replacement_pairs.length == 0) return;

    for (let i = 0; i < replacement_pairs.length; i++) {
        text_element.textContent = text_element.textContent.replace(
            replacement_pairs[i].key, replacement_pairs[i].value
        );
    }
}


function createI18NContainer(parent) {
    const text_container = document.createElement("div");
    text_container.className = "i18n"
    parent.appendChild(text_container);

    return text_container;
}


function addI18NTextToContainer(type, key, container, replace = []) {
    const text = document.createElement(type);
    text.className = key;
    text.textContent = GameConfig.text[key].translation;
    replaceI18NText(text, replace);
    container.appendChild(text);

    TEXT_REPLACEMENTS[key] = replace;

    return text;
}


function animate_shiftSteps(current_step, prev_step, next_step) {
    // Copy references to avoid timer issues.
    let current = current_step;
    let previous = prev_step;
    let next = next_step;

    if (previous) {
        previous.style.opacity = "0";
        setTimeout(function () { previous.remove(); }, 1000);
    }

    current.style.transform = "scale(0.5, 0.5) translateY(-90px)";
    current.style.opacity = "0.4"

    next.animate(
        [
            { opacity: "0", },
            { opacity: "1", }
        ],
        { duration: 800 }
    );
}

function animate_invalidSearch() {
    let search_box = GameConfig.search_box;

    search_box.style.borderColor = Color.RED;

    setTimeout(
        function () {
            search_box.style.borderColor = "";
            search_box.value = "";
            search_box.disabled = false;
        },
        1000
    );
}


function animate_nextRound(new_origin_id) {
    let station_select = GameConfig.current_step;
    let search_box = GameConfig.search_box;
    search_box.style.borderColor = Color.GREEN;

    let timeout_ms = 800;

    if (new_origin_id != GameConfig.origin_id) {
        const tunnel_container = createI18NContainer(station_select);
        const tunnel = addI18NTextToContainer(
            "p", "tunnel", tunnel_container,
            [
                { key: "$line", value: LINES[GameConfig.line_id].name },
                { key: "$tunnel_station", value: STATIONS[new_origin_id - 1].name },
            ]
        );
        tunnel.style.marginTop = "10px";

        timeout_ms = 4000;
    }

    setTimeout(
        function () {
            search_box.value = "";
            transitionToLine();
        },
        timeout_ms
    );
}


function animate_finalMessage(message, refresh, tunnel = null) {
    message.animate(
        [
            { opacity: "0" },
            { opacity: "1" }
        ],
        { duration: 200 }
    );

    refresh.animate(
        [
            { opacity: "0" },
            { opacity: "1" }
        ],
        { duration: 200 }
    );

    if (tunnel !== null) {
        tunnel.animate(
            [
                { opacity: "0" },
                { opacity: "1" }
            ],
            { duration: 200 }
        );
    }
}


function transitionToSuccess(new_origin_id) {
    if (GameConfig.state != State.STATION) return;

    let station_select = GameConfig.current_step;
    let search_box = GameConfig.search_box;

    const text_container = createI18NContainer(station_select);
    const success = addI18NTextToContainer("h2", "arrived", text_container);

    let tunnel = null;
    if (new_origin_id != GameConfig.origin_id) {
        tunnel = addI18NTextToContainer(
            "p", "tunnel", text_container,
            [
                { key: "$line", value: LINES[GameConfig.line_id].name },
                { key: "$tunnel_station", value: STATIONS[new_origin_id - 1].name },
            ]
        );
        tunnel.style.marginTop = "10px";
        tunnel.style.marginBottom = "10px";
    }

    const refresh = addI18NTextToContainer("p", "refresh", text_container);
    refresh.style.color = Color.DARK_GRAY;
    refresh.style.marginTop = "10px";

    setSearchBoxToFinalColor(search_box, Color.GREEN);

    animate_finalMessage(success, refresh);

    GameConfig.state = State.SUCCESS;
}


function transitionToFailure(failure_reason) {
    if (GameConfig.state != State.STATION) return;

    let station_select = GameConfig.current_step;

    const text_container = createI18NContainer(station_select);
    const failure = addI18NTextToContainer(
        "p", failure_reason, text_container,
        [
            { key: "$origin", value: STATIONS[GameConfig.origin_id - 1].name },
            { key: "$line", value: LINES[GameConfig.line_id].name },
            { key: "$destination", value: STATIONS[GameConfig.destination_id - 1].name },
        ]
    );
    failure.style.marginBottom = "10px";

    const refresh = addI18NTextToContainer("p", "refresh", text_container);
    refresh.style.color = Color.DARK_GRAY;
    refresh.style.marginTop = "10px";

    setSearchBoxToFinalColor(GameConfig.search_box, Color.RED);

    animate_finalMessage(failure, refresh);
    GameConfig.state = State.FAILURE;
}


function transitionToLine() {
    if (GameConfig.state != State.STATION) return;

    let steps_container = document.getElementById("steps-container");
    let station_step = GameConfig.current_step;

    const line_select = document.createElement("div");
    line_select.className = "step";
    line_select.style.transform = "translateY(30px)";

    const text_container = createI18NContainer(line_select);
    addI18NTextToContainer("span", "take-line", text_container);
    line_select.appendChild(document.createElement("p"));

    const current = addI18NTextToContainer(
        "span", "current-station", text_container,
        [{ key: "$station", value: STATIONS[GameConfig.destination_id - 1].name }]
    );
    current.style.textAlign = "right";
    current.style.float = "right";
    current.style.color = Color.DARK_GRAY;

    for (let i = 0; i < LINES_IN_ORDER.length; i++) {
        let line_id = LINES_IN_ORDER[i];
        const button = document.createElement("button");
        button.className = "metro-button";
        button.type = "button";
        button.style.background = "url(/svg/" + LINES[line_id].name + ".svg)";
        button.onclick = function (e) { event_metroLineClicked(e, line_id); };
        line_select.appendChild(button);
    }

    steps_container.appendChild(line_select);

    animate_shiftSteps(station_step, GameConfig.prev_step, line_select);

    GameConfig.prev_step = station_step;
    GameConfig.current_step = line_select;

    GameConfig.origin_id = GameConfig.destination_id;
    GameConfig.line_id = 0;
    GameConfig.destination_id = 0;

    GameConfig.state = State.LINE;
}


function transitionToStation(direction_id) {
    if (GameConfig.state != State.LINE && GameConfig.state != State.DIRECTION)
        return;

    let steps_container = document.getElementById("steps-container");

    const station_select = document.createElement("div");
    station_select.className = "step";
    station_select.style.transform = "translateY(30px)";

    const text_container = createI18NContainer(station_select);
    addI18NTextToContainer("p", "to-station", text_container);

    const input_box = document.createElement("input");
    input_box.type = "search";
    input_box.className = "search-box";
    input_box.placeholder = "Station…";
    input_box.addEventListener("keydown", event_stationSearchKeyDown);
    station_select.appendChild(input_box);

    steps_container.appendChild(station_select);

    animate_shiftSteps(GameConfig.current_step, GameConfig.prev_step, station_select);

    GameConfig.direction_id = direction_id;
    GameConfig.prev_step = GameConfig.current_step;
    GameConfig.current_step = station_select;
    GameConfig.search_box = input_box;
    GameConfig.state = State.STATION;
}


function transitionToDirection(line_id) {
    if (GameConfig.state != State.LINE)
        return;

    let steps_container = document.getElementById("steps-container");
    let selected_line = LINES[line_id];
    let line_step = GameConfig.current_step;

    if (!selected_line.termini)
        return;

    if (selected_line.termini.length < 2) {
        transitionToStation(selected_line.termini[0].id);
        return;
    }

    const direction_select = document.createElement("div");
    direction_select.className = "step";
    direction_select.style.transform = "translateY(30px)";

    const text_container = createI18NContainer(direction_select);
    addI18NTextToContainer("p", "towards", text_container);

    const button_wrapper = document.createElement("div");
    button_wrapper.className = "direction-button-wrap"
    direction_select.appendChild(button_wrapper);

    for (let i = 0; i < selected_line.termini.length; i++) {
        if (!("termini" in selected_line)) continue;

        let terminus = selected_line.termini[i];

        const button = document.createElement("button");
        button.type = "button";
        button.className = "direction-button";
        button.textContent = terminus.name;
        button.addEventListener("click", function (e) { event_directionClicked(e, terminus.id); });
        button_wrapper.appendChild(button);
    }

    steps_container.appendChild(direction_select);

    animate_shiftSteps(line_step, GameConfig.prev_step, direction_select);

    GameConfig.line_id = line_id;
    GameConfig.prev_step = line_step;
    GameConfig.current_step = direction_select;
    GameConfig.state = State.DIRECTION;
}


function validate() {
    let origin_id = GameConfig.origin_id;
    let destination_id = GameConfig.destination_id;
    let line_id = GameConfig.line_id;
    let direction_id = GameConfig.direction_id;

    if (origin_id == 0 ||
        destination_id == 0 ||
        line_id == 0 ||
        direction_id == 0
    ) {
        animate_invalidSearch();
        return;
    }

    fetch("/maison/verify/line/" + line_id + "/origin/" + origin_id + "/destination/" + destination_id + "/direction/" + direction_id,
        {
            method: 'GET',
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error: Received code: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            if (data.status === -1) {
                transitionToFailure(data.message);
            } else if (data.status === 0) {
                if (GameConfig.destination_id == GameConfig.final_id) {
                    transitionToSuccess(data.origin_id);
                } else {
                    animate_nextRound(data.origin_id);
                }
            }
        })
        .catch(error => {
            console.error(error);
            animate_invalidSearch();
        });
}

function stationSearch() {
    if (GameConfig.state != State.STATION) return;

    let search_box = GameConfig.search_box;

    if (search_box.value.trim().length == 0) {
        return;
    }

    search_box.disabled = true;

    let filtered_value = cleanInput(search_box.value);

    let results = fuse.search(filtered_value);

    if (results.length == 0) {
        animate_invalidSearch();
        return;
    }

    if (results.length > 1) {
        if (results[0].score > 0.005) {
            animate_invalidSearch();
            return;
        }
    }

    let filtered_result = cleanInput(results[0].item.name);
    if ((filtered_value.length / filtered_result.length) < 0.9) {
        if (results[0].item.tags.length > 0) {
            let max_ratio = 0.0;
            for (let tag of results[0].item.tags) {
                let ratio = filtered_value.length / tag.length;
                max_ratio = Math.max(ratio, max_ratio);
            }

            if (max_ratio < 0.9) {
                animate_invalidSearch();
                return;
            }
        } else {
            animate_invalidSearch();
            return;
        }
    }

    GameConfig.destination_id = results[0].item.id;

    validate();

}

function event_metroLineClicked(event, line) {
    if (GameConfig.state != State.LINE) return;

    event.target.style.borderWidth = "4px";

    let line_id = Number(line);

    let buttons = document.querySelectorAll(".metro-button");
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
    }

    transitionToDirection(line_id);
}


function event_directionClicked(event, direction) {
    if (GameConfig.state != State.DIRECTION) return;

    event.target.style.borderWidth = "3px";

    let direction_id = Number(direction);

    let buttons = document.querySelectorAll(".direction-button");
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
    }

    transitionToStation(direction_id);
}


function event_stationSearchKeyDown(event) {
    if (event.keyCode == 13) {
        stationSearch();
    }
}


window.onload = function () {
    init();
};