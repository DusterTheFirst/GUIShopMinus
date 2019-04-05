import Vue from "vue";
import colors from "./colorcodes.json";

// Test Vue App
let vm = new Vue({
    el: "#form",
    data: {
        title: ""
    },
    computed: {
        colors: function() {
            return Object.values(colors).filter(x => x.name.toLowerCase().includes(this.title.toLowerCase()));
        }
    },
    methods: {
        toColor: function(num: number) {
            num >>>= 0;
            var b = num & 0xFF,
                g = (num & 0xFF00) >>> 8,
                r = (num & 0xFF0000) >>> 16;
            return "rgb(" + [r, g, b].join(",") + ")";
        }
    }
});