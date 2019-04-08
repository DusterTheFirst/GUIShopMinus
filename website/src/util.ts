/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

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

/** Get the icon item through webpack */
export async function getItemIcon(id: number, meta: number = 0) {
    let { icons } = await import("./icons");

    return icons[`./${id}-${meta}.png`];
}