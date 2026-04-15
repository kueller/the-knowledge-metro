import { FR_TEXT } from "./locale/fr.js";
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
    prev_line_id = 0;

    current_step = null;
    prev_step = null;
    search_box = null;

    line_style_width = 0;
}

const GameConfig = new KnowledgeGameConfig();


function set_language(override_lang = null) {
    if (override_lang != null) {
        document.documentElement.lang = override_lang
    }

    let lang = document.documentElement.lang;

    if (lang == "fr") {
        GameConfig.text = FR_TEXT;
    } else if (lang == "en") {
        GameConfig.text = EN_TEXT;
    } else {
        return;
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

    const fr_button = document.getElementById("lang-fr");
    const en_button = document.getElementById("lang-en");

    fr_button.addEventListener(
        "change",
        function(e) { set_language(e.target.value); }
    )

    en_button.addEventListener(
        "change",
        function(e) { set_language(e.target.value); }
    )

    const progress_overlay = document.getElementById("progress-overlay");
    const progress_display_btn = document.getElementById("progress-display");
    const progress_close_btn = document.getElementById("progress-close");

    progress_overlay.style.removeProperty("opacity");
    progress_overlay.addEventListener("click", event_progressCloseClicked);
    progress_close_btn.addEventListener("click", event_progressCloseClicked);
    progress_display_btn.addEventListener("click", event_progressDisplayClicked);

    const info_title_btn = document.getElementById("info-title");
    const info_progress_btn = document.getElementById("info-progress");

    info_title_btn.addEventListener("click", event_infoButtonDisplayClicked);
    info_progress_btn.addEventListener("click", event_infoButtonDisplayClicked);

    const info_overelay = document.getElementById("info-overlay");
    const info_close_btn = document.getElementById("info-close");

    info_overelay.style.removeProperty("opacity");
    info_overelay.addEventListener("click", event_infoCloseClicked);
    info_close_btn.addEventListener("click", event_infoCloseClicked);

    GameConfig.line_style_width = parseInt(getComputedStyle(metro_buttons[0]).width);
    GameConfig.current_step = document.getElementById("line-select");
    GameConfig.state = State.LINE;
}


function cleanInput(input) {
    return input.trim()
        .replaceAll('-', ' ')
        .replaceAll('—', ' ')
        .replaceAll("'", ' ')
        .replaceAll('’', ' ')
        .split(/\s+/)
        .join(' ');
}


function variable_yshift(base) {
    const element_height = Math.trunc(GameConfig.current_step.getBoundingClientRect().height / 2) - 90;
    if (element_height >= base) {
        base = element_height + 20;
    }

    return base;
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


function createProgressLineIcon(line_id) {
    let line_shift = LINES_IN_ORDER.indexOf(line_id);

    const icon = document.createElement("img");
    icon.className = "progress-icon";
    icon.src = "./img/pixel.gif";
    icon.width = "1px";
    icon.height = "1px";
    icon.style.backgroundPosition = -1 * 18 * line_shift + "px 0";    

    return icon;
}


function createAndAddProgressRow(progress_list, line_id, station_id) {
    const p = document.createElement("p");

    if (line_id > 0) {
        const icon = createProgressLineIcon(line_id);
        p.appendChild(icon);
    } 

    const row = document.createElement("span");
    row.textContent = STATIONS[station_id - 1].name;
    p.appendChild(row);
    progress_list.appendChild(p);

    return p;
}


function createAndAddProgressArrow(progress_list) {
    const arrow = document.createElement("p");
    arrow.textContent = "↓";
    progress_list.appendChild(arrow);

    return arrow;
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

    appendProgress(new_origin_id);

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


function animate_appendProgress(new_elements) {
    new_elements.forEach((e) => e.style.opacity = "0");

    for (let i = 0; i < new_elements.length; i++) {
        setTimeout(
            function() {
                new_elements[i].style.opacity = "1";
                new_elements[i].animate(
                    [
                        { opacity: "0" },
                        { opacity: "1" }
                    ],
                    { duration: 200 }
                )
            },
            200 * i
        );
    }
}


function appendProgress(new_origin_id) {
    const progress_list = document.getElementById("progress-list");

    let new_elements = [];

    const origin_row = createAndAddProgressRow(progress_list, GameConfig.prev_line_id, GameConfig.origin_id);
    const arrow_origin = createAndAddProgressArrow(progress_list);

    new_elements.push(origin_row);
    new_elements.push(arrow_origin);

    if (new_origin_id != GameConfig.origin_id) {
        const tunnel_row = createAndAddProgressRow(progress_list, GameConfig.line_id, new_origin_id);
        const arrow_tunnel = createAndAddProgressArrow(progress_list);

        new_elements.push(tunnel_row);
        new_elements.push(arrow_tunnel);
    }

    const dest_row = createAndAddProgressRow(progress_list, GameConfig.line_id, GameConfig.destination_id);
    dest_row.style.marginBottom = "15px";
    new_elements.push(dest_row);

    const divider = document.createElement("hr");
    progress_list.appendChild(divider);
    new_elements.push(divider);

    animate_appendProgress(new_elements);
    progress_list.scrollTop = progress_list.scrollHeight;
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

    const refresh = addI18NTextToContainer("a", "refresh", text_container);
    refresh.href = "./"
    refresh.style.color = Color.DARK_GRAY;
    refresh.style.marginTop = "10px";

    setSearchBoxToFinalColor(search_box, Color.GREEN);

    appendProgress(new_origin_id);
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

    const refresh = addI18NTextToContainer("a", "refresh", text_container);
    refresh.href = "./"
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

    let yshift = variable_yshift(30);

    const line_select = document.createElement("div");
    line_select.className = "step";
    line_select.style.transform = "translateY(" + yshift + "px)";

    const text_container = createI18NContainer(line_select);
    addI18NTextToContainer("span", "take-line", text_container);

    const current = addI18NTextToContainer(
        "span", "current-station", text_container,
        [{ key: "$station", value: STATIONS[GameConfig.destination_id - 1].name }]
    );

    for (let i = 0; i < LINES_IN_ORDER.length; i++) {
        let line_id = LINES_IN_ORDER[i];
        const button = document.createElement("button");
        button.className = "metro-button";
        button.type = "button";
        button.style.backgroundPosition = -1 * GameConfig.line_style_width * i + "px 0";
        button.onclick = function (e) { event_metroLineClicked(e, line_id); };
        line_select.appendChild(button);
    }

    steps_container.appendChild(line_select);

    animate_shiftSteps(station_step, GameConfig.prev_step, line_select);

    GameConfig.prev_step = station_step;
    GameConfig.current_step = line_select;

    GameConfig.origin_id = GameConfig.destination_id;
    GameConfig.prev_line_id = GameConfig.line_id;
    GameConfig.line_id = 0;
    GameConfig.destination_id = 0;

    GameConfig.state = State.LINE;
}


function transitionToStation(direction_id) {
    if (GameConfig.state != State.LINE && GameConfig.state != State.DIRECTION)
        return;

    let steps_container = document.getElementById("steps-container");

    let yshift = variable_yshift(30);

    const station_select = document.createElement("div");
    station_select.className = "step";
    station_select.style.transform = "translateY(" + yshift + "px)";

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
        GameConfig.line_id = line_id;
        transitionToStation(selected_line.termini[0].id);
        return;
    }

    let yshift = variable_yshift(30);

    const direction_select = document.createElement("div");
    direction_select.className = "step";
    direction_select.style.transform = "translateY(" + yshift + "px)";

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

    animate_shiftSteps(line_step, GameConfig.prev_step, direction_select, true);

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
            credentials: "same-origin",
            headers: { lang: document.documentElement.lang },
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

    event.target.style.boxShadow = "0 0 1px 4px black";

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


function event_progressDisplayClicked() {
    const progress = document.getElementById("progress-window");
    progress.style.opacity = "1";
    progress.classList.add("active");

    const overlay = document.getElementById("progress-overlay");
    overlay.classList.add("active");
}


function event_progressCloseClicked(event) {
    if (event.target.id == "progress-overlay" || event.target.id == "progress-close") {
        const overlay = document.getElementById("progress-overlay");
        const progress = document.getElementById("progress-window");
        overlay.classList.remove("active");
        progress.classList.remove("active");

        setTimeout(() => { progress.style.removeProperty("opacity"); }, 300);
    }
}


function event_infoButtonDisplayClicked() {
    const overlay = document.getElementById("info-overlay");
    overlay.classList.add("active");
}


function event_infoCloseClicked(event) {
    const overlay = document.getElementById("info-overlay");
    if (event.target.id == "info-overlay" || event.target.id == "info-close") {
        overlay.classList.remove("active");
    }
}


function event_stationSearchKeyDown(event) {
    if (event.keyCode == 13) {
        stationSearch();
    }
}


window.onload = function () {
    init();
};