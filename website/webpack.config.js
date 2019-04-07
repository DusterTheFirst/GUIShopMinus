const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CleanWebpackPlugin = require("clean-webpack-plugin");
const webpack = require("webpack");
const CopyWebpackPlugin = require("copy-webpack-plugin");

module.exports = {
    entry: "./src/app.ts",
    devtool: "inline-source-map",
    module: {
        rules: [
            {
                test: /\.ts$/,
                use: "ts-loader",
                exclude: /node_modules/
            },
            {
                test: /\.ts$/,
                enforce: "pre",
                use: [
                    {
                        loader: 'tslint-loader',
                        options: {
                            typeCheck: true
                        }
                    }
                ],
                exclude: /node_modules/
            },
            {
                test: /\.(png|svg|jpg|gif)$/,
                use: 'file-loader'
            }
        ]
    },
    resolve: {
        extensions: [".tsx", ".ts", ".js"]
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
        }),
        new webpack.HotModuleReplacementPlugin()
    ],
    devServer: {
        contentBase: "./dist",
        hot: true
    },
    mode: "development",
    resolve: {
        alias: {
            vue: "vue/dist/vue.js"
        }
    }
};