const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CleanWebpackPlugin = require("clean-webpack-plugin");
const webpack = require("webpack");
const CopyWebpackPlugin = require("copy-webpack-plugin");

module.exports = {
    entry: "./src/app.ts",
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                enforce: "pre",
                use: [
                    { loader: "ts-loader" },
                    {
                        loader: "tslint-loader",
                        options: {
                            typeCheck: true
                        }
                    }
                ],
                exclude: /node_modules/
            },
            {
                // Load items as data urls in order to speed up download times
                test: /\.png$/,
                use: "url-loader",
                include: /items/
            },
            {
                test: /\.(png|svg|jpg|gif|eot|ttf|woff)$/,
                use: "file-loader",
                exclude: /items/
            }
        ]
    },
    output: {
        filename: "[name].[hash].js",
        path: path.resolve(__dirname, "dist")
    },
    plugins: [
        new CleanWebpackPlugin(),
        new CopyWebpackPlugin([
            "public"
        ]),
        new HtmlWebpackPlugin({
            template: "public/index.html"
        })
    ],
    resolve: {
        alias: {
            vue: "vue/dist/vue.js"
        },
        extensions: [".tsx", ".ts", ".js"]
    }
};