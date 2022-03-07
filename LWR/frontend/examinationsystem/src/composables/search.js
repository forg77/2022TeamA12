import {ref, watch} from 'vue'

let searchString = ref('搜索');
let cancelString = ref('取消搜索');

let searchText = ref('');
let extraData = ref({search: null});
let searchButtonString = ref(searchString.value);
let searchCallback = ref(() => {
});

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

watch(searchText, (newVal) => {
    if (newVal === "" && extraData.value.search != null) {
        extraData.value.search = null;
        searchButtonString.value = searchString.value;
        searchCallback.value();
    }
});

export function getSearchInfo() {
    return {searchString, cancelString, searchText, extraData, searchButtonString, searchCallback, onSearchButtonClick};
}

export {}
