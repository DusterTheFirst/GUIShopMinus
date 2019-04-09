/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

import { IMaterial, IMaterialInfo } from "./app.js";
import items from "./items.json";
import mccodes from "./mccodes.json";

export function zip<A, B>(arrayA: A[], arrayB: B[]) {
    return arrayA.map((element, index) => {
        return {
            ...element,
            ...arrayB[index]
        };
    });
}

export interface IMapObject<V> {
    [key: string]: V;
}

export function zipObject<V>(object: IMapObject<V>) {
    let keys = Object.keys(object);

    return keys.map((key) => {
        return {
            key,
            value: object[key]
        };
    });
}

export function fuzzySearch(value: string, search: string) {
    return value.toLowerCase().includes(search.toLowerCase());
}

const colorcodes = mccodes.colors as IMapObject<{ color: string; name: string }>;

export function parseColorCodesToHTML(stringcolorcodes: string): string {
    return stringcolorcodes.replace(/&([0-9a-f])(.{0,}?)(?=(?:&[0-9a-f])|$)/g, (_, code: string, text: string) => {
        let span = document.createElement("span");

        span.style.color = colorcodes[code].color;
        span.innerText = text;

        return span.outerHTML.replace(/&amp;/g, "&");
    }).replace(/((?:\&[lnomkr])+)([^&<]*)/g, (_, rawcodes: string, text: string) => {
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

export const icons: IMapObject<string> = (() => {
    let context = require.context("./items/", true, /\.png$/);

    let obj: IMapObject<string> = {};

    context.keys().map(key => obj[key] = context(key) as string);

    return obj;
})();

/** Get the icon item through webpack */
export function getItemIcon(material: IMaterial) {
    return icons[`./${material.type}-${material.meta}.png`];
}

const itemlist = items as IMaterialInfo[];

/** Get the icon item through webpack */
export function getItemInfo(material: IMaterial) {
    return items.find(x => x.type === material.type && x.meta === material.type);
}