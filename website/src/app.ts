/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

import Vue from "vue";
import VueLazyload from "vue-lazyload";
import colorcodes from "./colorcodes.json";
import items from "./items.json";
import { fuzzySearch, zipObject } from "./util";

const colorsZipped = zipObject<{ color: number; name: string }>(colorcodes).map(x => ({
    code: x.key,
    ...x.value
}));

Vue.use(VueLazyload);

let store = {
    title: "&8Store &3to &bbeat&4red&8men"
};

// tslint:disable: no-invalid-this
let vm = new Vue({
    computed: {
        coloredTitle() {
            enum ColorParseState {
                Normal,
                Color
            }
            let state = ColorParseState.Normal;
            let colors: { code: string; index: number }[] = [];
            this.store.title.split("").forEach((char, index) => {
                // Switch based on state
                if (state === ColorParseState.Normal) {
                    // If char is &, enter color parse mode
                    if (char === "&") {
                        state = ColorParseState.Color;
                    }
                    // Or else continue onward
                } else {
                    // Check if the char is a color code
                    if (colorsZipped.some(x => x.code === char)) {
                        colors.push({
                            code: char,
                            index: index - 1
                        });
                    }
                    // Reset the state to normal
                    state = ColorParseState.Normal;
                }
            });

            let newtitle = this.store.title.slice();
            for (let color of colors) {
                newtitle += this.store.title.substr(0, color.index);
            }

            return [colors, newtitle];
        }
    },
    data: {
        colors: colorsZipped,
        store
    },
    el: "#app",
    methods: {
        // tslint:disable: no-bitwise no-parameter-reassignment
        toColor(num: number) {
            num >>>= 0;
            let b = num & 0xFF;
            let g = (num & 0xFF00) >>> 8;
            let r = (num & 0xFF0000) >>> 16;

            return `rgb(${r},${g},${b})`;
        }
        // tslint:enable: no-bitwise, no-parameter-reassignment
    }
});
// tslint:enable: no-invalid-this