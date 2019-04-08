/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

import Vue from "vue";
import VueLazyload from "vue-lazyload";
import colors from "./colorcodes.json";
import items from "./items.json";
import { fuzzySearch, zipObject } from "./util";

const colorsZipped = zipObject<{ color: number; name: string }>(colors);

Vue.use(VueLazyload);

// Test Vue App
// tslint:disable: no-invalid-this
let vm = new Vue({
    computed: {
        colors() {
            return colorsZipped.map(color => {
                return {
                    color: color.value.color,
                    colorcode: color.key,
                    name: color.value.name,
                    show: fuzzySearch(color.value.name, this.search)
                };
            });
        },
        items() {
            return items.map(x =>
                ({
                    image: `./items/${x.type}-${x.meta}.png`,
                    info: `${x.name} (${x.text_type}): ${x.type}:${x.meta}`,
                    name: x.name,
                    show: fuzzySearch(x.name, this.search)
                })
            );
        }
    },
    data: {
        search: ""
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