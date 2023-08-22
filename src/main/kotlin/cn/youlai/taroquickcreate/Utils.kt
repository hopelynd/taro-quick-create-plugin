package cn.youlai.taroquickcreate

import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager

object Utils {
    fun getSelectedDirectory(dataContext: DataContext): String? {
        return CommonDataKeys.VIRTUAL_FILE.getData(dataContext)?.path
    }

    fun capitalizeFirstLetter(input: String): String {
        if (input.isEmpty()) {
            return input
        }
        val firstChar = input[0]
        val capitalizedFirstChar = firstChar.uppercaseChar()
        return capitalizedFirstChar + input.substring(1)
    }

    fun decapitalizeFirstLetter(input: String): String {
        if (input.isEmpty()) {
            return input
        }
        val firstChar = input[0]
        val decapitalizedFirstChar = firstChar.lowercaseChar()
        return decapitalizedFirstChar + input.substring(1)
    }

    fun getDefaultTemplateContent(fileName: String, folderName: String): String {
        val funName = capitalizeFirstLetter(folderName);
        val className = decapitalizeFirstLetter(folderName);
        // 根据需要为不同的文件名提供不同的默认模板内容
        return when (fileName) {
            "index.tsx" -> "import { View } from '@tarojs/components';\n" +
                    "\n" +
                    "import styles from './index.module.less';\n" +
                    "\n" +
                    "const $funName = () => {\n" +
                    "  return <View className={styles.$className}>$funName</View>;\n" +
                    "};\n" +
                    "\n" +
                    "export default $funName;"
            "index.module.less" -> ".$className {\n" +
                    "  \n" +
                    "}"
            "index.config.ts" -> "export default definePageConfig({\n" +
                    "  navigationBarTitleText: '$folderName',\n" +
                    "});"
            else -> ""
        }
    }

    fun refreshProject(path: String) {
        // 刷新项目目录
        LocalFileSystem.getInstance().refreshAndFindFileByPath(path!!)

    }
}