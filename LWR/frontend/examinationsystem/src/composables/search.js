import {ref, watch} from 'vue'

export function getSearchInfo() {
    function onSearchButtonClick() {
        if (extraData.value.search == null) {
            if (searchText.value !== "") {
                extraData.value.search = this.searchText;
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

    let searchString = ref('搜索');
    let cancelString = ref('取消搜索');

    let searchText = ref('');
    let extraData = ref({search: null});
    let searchButtonString = ref(searchString.value);
    let searchCallback = ref(() => {
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
