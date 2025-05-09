#!/bin/bash

DEVICE="/dev/sdb1"
MOUNT_POINT="/media/usb-MOUNTIFY"
LOG_FILE="/home/keshav/Mountify_Project/logs/mountify.log"

# Create mount point if it doesn't exist
if [ ! -d "$MOUNT_POINT" ]; then
    mkdir -p "$MOUNT_POINT"
    echo "[INFO] $(date): Created mount point $MOUNT_POINT" >> "$LOG_FILE"
fi

# Try mounting the device
if mount "$DEVICE" "$MOUNT_POINT"; then
    echo "[INFO] $(date): Successfully mounted $DEVICE at $MOUNT_POINT" >> "$LOG_FILE"
else
    echo "[ERROR] $(date): Failed to mount $DEVICE" >> "$LOG_FILE"
fi
