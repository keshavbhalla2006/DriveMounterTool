#!/bin/bash

MOUNT_DIR="/mnt/usb"
SCRIPTS_DIR="$(dirname "$0")"  # Current script's directory

# Check if USB is mounted at MOUNT_DIR
DEVICE=$(mount | grep "$MOUNT_DIR" | awk '{print $1}')

if [ -z "$DEVICE" ]; then
    echo "[ERROR] No USB device mounted at $MOUNT_DIR"
    exit 1
fi

echo "[INFO] Detected mounted USB device: $DEVICE"

# Attempt to unmount
echo "[INFO] Attempting to unmount $DEVICE from $MOUNT_DIR..."
if sudo umount "$MOUNT_DIR"; then
    echo "[SUCCESS] USB unmounted from $MOUNT_DIR"

    # Show popup notification
    "$SCRIPTS_DIR/notify_popup.sh" "$DEVICE" "$MOUNT_DIR" "UNMOUNTED" &

    # Log event
    "$SCRIPTS_DIR/log_event.sh" "$DEVICE" "UNMOUNTED"

    # Send email notification
    "$SCRIPTS_DIR/send_email.sh" "$DEVICE" "$MOUNT_DIR" "UNMOUNTED"

else
    echo "[ERROR] Failed to unmount $DEVICE"
fi
