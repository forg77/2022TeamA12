<template lang="html">
  <div class="editor">
    <!-- <div ref="toolbar" class="toolbar"></div> -->
    <div ref="editor" class="text"></div>
  </div>
</template>

<script>
  import E from 'wangeditor'
  export default {
    name: 'editoritem',
    data() {
      return {
        // uploadPath,
        editor: null,
        info_: null
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    props: {
      value: {
        type: String,
        default: ''
      },
      isClear: {
        type: Boolean,
        default: false
      }
    },
    watch: {
      isClear(val) {
        // 触发清除文本域内容
        if (val) {
          this.editor.txt.clear()
          this.info_ = null
        }
      },
      value: function(value) {
        if (value !== this.editor.txt.html()) {
          this.editor.txt.html(this.value)
        }
      }
      //value为编辑框输入的内容，这里我监听了一下值，当父组件调用得时候，如果给value赋值了，子组件将会显示父组件赋给的值
    },
    mounted() {
      this.seteditor()
    },

    methods: {
      seteditor() {
        this.editor = new E( this.$refs.editor)
        // this.editor.customConfig = this.editor.customConfig ? this.editor.customConfig : this.editor.config;
        // 配置 server 上传图片接口地址
        // this.editor.config.uploadImgServer = '/upload-img'
        this.editor.config.onchange = (html) => {
          this.info_ = html // 绑定当前值
          this.$emit('change', this.info_) // 将内容同步到父组件中
        }


        // 自定义 alert
        this.editor.config.customAlert = function (s, t) {
          switch (t) {
            case 'success':
              this.$Message.success(s)
              break
            case 'info':
              this.$Message.info(s)
              break
            case 'warning':
              this.$Message.warning(s)
              break
            case 'error':
              this.$Message.error(s)
              break
            default:
              this.$Message.info(s)
              break
          }
        }
        // 配置全屏功能按钮是否展示
       this.editor.config.showFullScreen = false

        // 创建富文本编辑器
        this.editor.create()
        this.editor.txt.html(this.value) // 重新设置编辑器内容
      }
    }
  }
</script>
<style lang="scss">
  .editor {
    width: 100%;
    margin: 0 auto;
    position: relative;
    z-index: 0;
  }
  .toolbar {
    border: 1px solid #ccc;
  }
  .text {
    border: 1px solid #ccc;
     min-height: 200px;
    .w-e-text-container{
      height: 200px!important;
    }
    .w-e-text{
      min-height: 200px;
    }
  }
</style>
