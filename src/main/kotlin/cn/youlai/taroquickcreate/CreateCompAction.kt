package cn.youlai.taroquickcreate

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.io.File
import java.nio.file.Files


class CreateCompAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val basePath = Utils.getSelectedDirectory(event.dataContext)

        val folderName = Messages.showInputDialog(
                project,
                "Enter the folder name:",
                "Create Folder",
                Messages.getQuestionIcon()
        )


        if (!folderName.isNullOrBlank()) {
            val folderPath = "$basePath/"+Utils.capitalizeFirstLetter(folderName)
            val folder = File(folderPath)

            if (folder.mkdir()) {
                val files = listOf("index.tsx", "index.module.less")

                files.forEach { fileName ->
                    val filePath = "$folderPath/$fileName"
                    val file = File(filePath)

                    if (file.createNewFile()) {
                        val templateContent = Utils.getDefaultTemplateContent(fileName, folderName)
                        Files.write(file.toPath(), templateContent.toByteArray())
                        println("Created file: $fileName")
                    } else {
                        println("Failed to create file: $fileName")
                    }
                }

                Utils.refreshProject(folderPath)
                Messages.showInfoMessage("Folder and files created successfully.", "Success")
            } else {
                Messages.showErrorDialog("Failed to create folder.", "Error")
            }
        }
    }
}