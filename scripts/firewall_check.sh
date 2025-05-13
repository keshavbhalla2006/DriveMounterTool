#!/bin/bash

MOUNT_POINT=$1

echo "[INFO] Running custom firewall-style checks on $MOUNT_POINT..."

echo "[CHECK] Executable files:"
find "$MOUNT_POINT" -type f -perm /111

echo "[CHECK] Suspicious file types (e.g., .sh, .exe, .bat):"
find "$MOUNT_POINT" -type f -iname "*.sh" -o -iname "*.exe" -o -iname "*.bat"

echo "[CHECK] Files with SUID/SGID:"
find "$MOUNT_POINT" -perm /6000
