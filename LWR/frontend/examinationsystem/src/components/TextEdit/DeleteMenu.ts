// 引入 wangEditor
import E from 'wangeditor'
import Editor from "wangeditor"; // npm 安装

const {$, BtnMenu, DropListMenu, PanelMenu, DropList, Panel, Tooltip} = E

// 第一，菜单 class ，Button 菜单继承 BtnMenu class
export class DeleteMenu extends BtnMenu {
    constructor(editor: Editor, deleteString: string = '删除') {
        // data-title属性表示当鼠标悬停在该按钮上时提示该按钮的功能简述
        // require()
        const $elem = E.$(
            `<div style="margin-left:auto;justify-self: flex-end" class="w-e-menu" data-title="${deleteString}">
                    <img alt="删除" src="${require('@/assets/image/delete.png')}" width="22" height="22"/>
                </div>`
        )
        super($elem, editor);
    }
}
