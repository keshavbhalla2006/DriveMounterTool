#!/bin/bash

DEVICE="$1"

if [ -z "$DEVICE" ]; then
    echo "[ERROR] No device specified."
    echo "Usage: $0 /dev/sdc1"
    exit 1
fi

# Find mount point
MOUNT_POINT=$(mount | grep "$DEVICE" | awk '{print $3}')

if [ -z "$MOUNT_POINT" ]; then
    echo "[INFO] $DEVICE is not mounted."
    exit 0
fi

# Attempt to unmount
echo "[INFO] Unmounting $DEVICE from $MOUNT_POINT..."
if sudo umount "$DEVICE"; then
    echo "[SUCCESS] $DEVICE unmounted successfully."
else
    echo "[ERROR] Failed to unmount $DEVICE."
    exit 1
fi
