/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

import "normalize.css";
import Vue from "vue";
import "./styles.scss";
import { fuzzySearch, getItemIcon, IMapObject, parseColorCodesToHTML, zipObject } from "./util";

(async () => {
    const items = (await import(/* webpackPreload: true */ "./items.json")).default;
    const mccodes = await import(/* webpackPreload: true */ "./mccodes.json");

    const colorsZipped = zipObject(mccodes.colors).map(x => ({
        code: x.key,
        ...x.value
    }));

    const stylesZipped = zipObject(mccodes.styles).map(x => ({
        code: x.key,
        ...x.value
    }));

    let store = {
        title: "&8&eStore &3to &bbeat &4red &9&l&n&m&k&omen"
    };

    let vm = new Vue({
        computed: {
            coloredTitle() {
                return parseColorCodesToHTML(this.store.title);
            }
        },
        data: {
            colors: colorsZipped,
            items: items.map(x => getItemIcon(x.type, x.meta)),
            store,
            styles: stylesZipped
        },
        el: "#app"
    });
})().catch(e => console.error(e));
