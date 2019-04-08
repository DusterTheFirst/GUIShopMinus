/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

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