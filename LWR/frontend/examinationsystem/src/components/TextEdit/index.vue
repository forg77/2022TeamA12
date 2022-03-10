<template lang="html">
  <div class="editor">
    <!-- <div ref="toolbar" class="toolbar"></div> -->
    <div ref="editor" class="text"></div>
  </div>
</template>

<script lang="ts">
import E from "wangeditor";
import {defineComponent} from "vue";
import Editor from "wangeditor";
import {DomElementSelector} from "wangeditor/dist/utils/dom-core";
import {DeleteMenu} from "@/components/TextEdit/DeleteMenu";

export default defineComponent({
  data() {
    return {
      // uploadPath,
      editor: null as Editor | null,
    };
  },
  emits: ['update:content','deleteClick'],
  props: {
    content: String,
    deleteMenu: {
      type: Boolean,
      default: false
    },
    deleteString: {
      type: String,
      default: '删除选项'
    }
  },
  watch: {
    content: {
      handler(value) {
        // console.log(value);
        if (value != this.editor?.txt.html()) this.editor?.txt.html(value);
      },
    },
    //value为编辑框输入的内容，这里我监听了一下值，当父组件调用得时候，如果给value赋值了，子组件将会显示父组件赋给的值
  },
  mounted() {
    this.setEditor();
    this.editor?.txt.html(this.content);
  },

  methods: {
    setEditor() {
      this.editor = new E(this.$refs.editor as DomElementSelector);
      this.editor.config.height = 150;
      // 配置 server 上传图片接口地址
      this.editor.config.onchange = (html: string) => {
        this.$emit("update:content", html); // 将内容同步到父组件中
      };

      this.editor.config.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        // 'quote',  // 引用
        // 'emoticon',  // 表情
        'image',  // 插入图片
        'table',  // 表格
        // 'video',  // 插入视频
        'code',  // 插入代码
        'undo',  // 撤销
        'redo'  // 重复
      ];


      //添加删除按钮
      let self = this;
      let DeleteMenuWithTag = class extends DeleteMenu {
        constructor(editor: Editor) {
          super(editor, self.deleteString);
        }
        clickHandler() {
          self.$emit('deleteClick');
        }
      };
      if (this.deleteMenu) {
        this.editor.menus.extend('delete', DeleteMenuWithTag);
        this.editor.config.menus.push('delete');
      }

      // 配置全屏功能按钮是否展示
      this.editor.config.showFullScreen = false;
      this.editor.config.zIndex = 0;

      // 创建富文本编辑器
      this.editor.create();
    },
  },
});
</script>
<style lang="scss" scoped>
.editor {
  // width: 500px;
  // height:50px;
  // margin: 0 auto;
  // position: relative;
  z-index: 0;

}

.toolbar {
  border: 1px solid #ccc;
}

.text {
  border: 1px solid #ccc;
  //  min-height: 100px;
  // height:200px;
  width: 100%;

  .w-e-text-container {
    height: 200px !important;
  }

  .w-e-text {
    min-height: 200px;
  }
}
</style>
