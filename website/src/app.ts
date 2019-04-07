/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

import Vue from "vue";
import colors from "./colorcodes.json";
import items from "./items.json";

// tslint:disable: no-invalid-this

// Test Vue App
let vm = new Vue({
    computed: {
        colors() {
            return Object.values(colors).filter(x => x.name.toLowerCase().includes(this.title.toLowerCase()));
        }
    },
    data: {
        items: items.map(x =>
            ({
                image: `./images/${x.type}-${x.meta}`,
                info: `${x.name} (${x.text_type}): ${x.type}:${x.meta}`,
                name: x.name
            })
        ),
        title: ""
    },
    el: "#form",
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