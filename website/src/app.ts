/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

import Vue from "vue";
import VueLazyload from "vue-lazyload";
import colorcodesraw from "./colorcodes.json";
import items from "./items.json";
import "./styles.scss";
import { fuzzySearch, IMapObject, zipObject } from "./util";

const colorcodes = colorcodesraw as IMapObject<{ color: string; name: string }>;
const colorsZipped = zipObject(colorcodes).map(x => ({
    code: x.key,
    ...x.value
}));

Vue.use(VueLazyload);

let store = {
    title: "&8&eStore &3to &bbeat &4red &9&l&n&m&k&omen"
};

let vm = new Vue({
    computed: {
        coloredTitle() {
            return this.store.title.replace(/((?:\&[0-9a-flnomkr])+)([^&]*)/g, (_, rawcodes: string, text: string) => {
                let span = document.createElement("span");

                let codes = rawcodes.replace(/&/g, "").split("");

                // Add bold if there is a bold code
                if (codes.includes("l")) {
                    span.style.fontWeight = "bold";
                }
                // Add underline and strike if m and n
                if (codes.includes("n") && codes.includes("m")) {
                    span.style.textDecoration = "underline line-through";
                } else {
                    // Add underline if there is an underline code
                    if (codes.includes("n")) {
                        span.style.textDecoration = "underline";
                    }
                    // Add a line through if m
                    if (codes.includes("m")) {
                        span.style.textDecoration = "line-through";
                    }
                }
                // Add italic if there is an oblique code
                if (codes.includes("o")) {
                    span.style.fontStyle = "italic";
                }

                // NO OBSTUF support
                // if (codes.includes("k")) {
                // }

                // No reset support
                // if (codes.includes("r")) {
                // }

                // Add the colors, only showing the last one given
                let color = codes.filter(code => Object.keys(colorcodes).includes(code)).pop();
                if (color !== undefined) {
                    span.style.color = colorcodes[color].color;
                }

                span.innerText = text;

                return span.outerHTML;
            });
        }
    },
    data: {
        colors: colorsZipped,
        store
    },
    el: "#app"
});
