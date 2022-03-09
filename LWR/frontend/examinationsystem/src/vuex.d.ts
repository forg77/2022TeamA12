// vuex.d.ts
import { ComponentCustomProperties } from 'vue'
import { Store } from 'vuex'
import {Config} from "@/config";

declare module '@vue/runtime-core' {
    // 声明自己的 store state
    interface State {
        // count: number,
        config:Config
    }

    // 为 `this.$store` 提供类型声明
    interface ComponentCustomProperties {
        $store: Store<State>
    }
}
