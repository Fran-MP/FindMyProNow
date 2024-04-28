package com.findmypronow.room

class MemberExistsException: Exception(
    "Existe un usuario en la sala con tu mismo nombre de usuario"
)