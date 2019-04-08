/*!
 * Copyright (C) 2019  Zachary Kohnen
 */

export const icons: { [x: string]: string } = (() => {
    let context = require.context("./items/", true, /\.png$/);

    let obj: { [x: string]: string } = {};

    context.keys().map(key => obj[key] = context(key) as string);

    return obj;
})();