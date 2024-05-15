package org.example.output

import org.example.entity.UserEntity

class Console: IOutputInfo {

    override fun showMessage(message: String, lineBreak: Boolean) {
        if (lineBreak) {
            println(message)
        } else {
            print(message)
        }
    }

    override fun show(userList: List<UserEntity>, message: String) {
        if (userList != null) {
            if (userList.isNotEmpty()) {
                showMessage(message,true)
                userList.forEachIndexed { index, user ->
                    showMessage("${index + 1}. $user",true)
                }
            } else {
                showMessage("No users found!",true)
            }
        }
    }
}