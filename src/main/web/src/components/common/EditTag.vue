<template>
    <div class="edit-tag without-error">
        <el-tag
                :key="tag"
                type="primary"
                v-for="tag in tags"
                :closable="true"
                :close-transition="false"
                @close="handleClose(tag)"
        >
            {{tag}}
        </el-tag>
        <el-input
                class="input-new-tag"
                v-if="inputVisible"
                v-model="inputValue"
                ref="saveTagInput"
                size="mini"
                @keyup.enter.native="handleInputConfirm"
                @blur="handleInputConfirm"
        ></el-input>
    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 添加</el-button>
    </div>
</template>
<script>
  /**
   *  该组件不是ItemRender渲染的组件
   */
  import Vue from 'vue'
  import {Component} from 'vue-property-decorator'
  @Component({
    props: ['tags']
  })
  export default class EditTag extends Vue {
    inputVisible = false
    inputValue = ''

    handleClose (tag) {
      this.inputValue = ''
      this.tags.splice(this.tags.indexOf(tag), 1)
    }

    showInput () {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    }

    handleInputConfirm () {
      let inputValue = this.inputValue
      if (inputValue) {
        this.tags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    }
  }

</script>
<style rel="stylesheet/less" lang="less">
    .edit-tag {
        .el-tag {
            border-radius: 0;
            min-width: 60px;
        }
        .el-tag+.el-tag {
            margin-left: 10px;
        }
        .button-new-tag,.input-new-tag {
            margin-left: 10px;
            width: 100px;
        }
    }
</style>
