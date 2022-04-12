module.exports = {
    "env": {
        "browser": true,
        "es6": true
    },
    "extends": [
        "eslint:recommended",
        "plugin:vue/essential",
        "plugin:@typescript-eslint/eslint-recommended"
    ],
    "globals": {
        "Atomics": "readonly",
        "SharedArrayBuffer": "readonly"
    },
    "parserOptions": {
        "ecmaVersion": 2018,
        "parser": "@typescript-eslint/parser",
        "sourceType": "module"
    },
    "plugins": [
        "vue",
        "@typescript-eslint"
    ],
    "rules": {
        "vue/no-mutating-props":"off",
        "vue/multi-word-component-names":"off",
        "vue/no-unused-components":"off",
        "no-unused-vars":"off",
        "vue/no-v-model-argument":"off",
        "vue/no-multiple-template-root":"off",
        "vue/no-v-for-template-key":"off",
        "no-undef":"off",
        "no-empty":"off"
    }
};
