#!/bin/bash

LOG_FILE="/home/keshav/Mountify_Project/logs/mountify.log"

echo "[INFO] $(date): USB device event detected." >> "$LOG_FILE"

# Example: $1 might be the device name passed via udev
DEVICE="$1"

if [[ -n "$DEVICE" ]]; then
    echo "[INFO] Device detected: $DEVICE" >> "$LOG_FILE"
    /home/keshav/Mountify_Project/scripts/mount_usb.sh "$DEVICE"
fi
