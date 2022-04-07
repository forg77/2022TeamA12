<template>
  <div>
    <DialogBox v-model:show="showDialog">
      <template v-slot:header>
        标题
      </template>
      这是一串长长的正文。这是一串长长的正文。这是一串长长的正文。这是一串长长的正文。
      <template v-slot:bottom>
        <button class="btn" @click="showDialog=false">确定</button>
      </template>
    </DialogBox>
    <Card>
      <template v-slot:headerLeft> 列表 </template>
      <template v-slot:content>
        <div class="content">
          <button class="btn" @click="showDialog=true">打开对话框</button>
        </div>
        <el-upload :with-credentials="true" :on-success="uploadSuccess" action="http://139.9.58.231:8080/supervision/uploadImg?examId=1&userId=1">
          <el-button type="primary">Click to upload</el-button>
        </el-upload>
      </template>
    </Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import DialogBox from "@/components/DialogBox.vue";
export default {
  data() {
    return {
      showDialog:false
    };
  },
  components: {
    Card,
    DialogBox
  },
  methods:{
    uploadSuccess(res){
      console.log(res);
    }
  }
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}
</style>
