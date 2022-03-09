import {Ref, ref, watch} from 'vue'

export interface SearchInfo {
    searchString: Ref<string>;
    cancelString: Ref<string>;
    searchText: Ref<string>;
    extraData: Ref;
    searchButtonString: Ref<string>;
    searchCallback: Ref<() => void>;

    onSearchButtonClick(): void;
}

export function getSearchInfo(): SearchInfo {
    function onSearchButtonClick() {
        if (extraData.value.search == null) {
            if (searchText.value !== "") {
                extraData.value.search = searchText.value;
                searchButtonString.value = cancelString.value;
                // this.$refs.examTable.getExams();
                searchCallback.value();
            }
        } else {
            extraData.value.search = null;
            searchButtonString.value = searchString.value;
            searchCallback.value();
        }
    }

    const searchString = ref('搜索');
    const cancelString = ref('取消搜索');

    const searchText = ref('');
    const extraData: Ref = ref({search: null});
    const searchButtonString = ref(searchString.value);
    const searchCallback = ref(() => {
    });

    watch(searchText, (newVal) => {
        if (newVal === "" && extraData.value.search != null) {
            extraData.value.search = null;
            searchButtonString.value = searchString.value;
            searchCallback.value();
        }
    });

    return {searchString, cancelString, searchText, extraData, searchButtonString, searchCallback, onSearchButtonClick};
}

// export {}
