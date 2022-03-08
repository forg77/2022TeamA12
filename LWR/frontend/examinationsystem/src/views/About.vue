<template>
  <div class="about">
    <h1>This is an about page</h1>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue'

interface Book {
  title: string
  author: string
  year: number
}

export default defineComponent({
  data() {
    return {
      book: {
        title: 'Vue 3 Guide',
        author: 'Vue Team',
        year: 2020,
      } as Book
    }
  },
})
</script>
