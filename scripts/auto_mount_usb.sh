#!/bin/bash

MOUNT_DIR="/mnt/usb"
LOG_FILE="/home/keshav/Mountify_Project/logs/mountify.log"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Detect USB
DEVICE=$(lsblk -nrpo NAME,RM,TYPE | awk '$2=="1" && $3=="part" {print $1}' | head -n 1)

if [ -z "$DEVICE" ]; then
    echo "[ERROR] No USB partition found!"
    echo "[ERROR] No USB partition found!" >> "$LOG_FILE"
    exit 1
fi

echo "[INFO] Detected USB device: $DEVICE"
echo "[INFO] Detected USB device: $DEVICE" >> "$LOG_FILE"

# Create mount directory if it doesn't exist
if [ ! -d "$MOUNT_DIR" ]; then
    sudo mkdir -p "$MOUNT_DIR"
    echo "[INFO] Created mount directory: $MOUNT_DIR" >> "$LOG_FILE"
fi

# Mount USB
echo "[INFO] Attempting to mount $DEVICE to $MOUNT_DIR..."
echo "[INFO] Attempting to mount $DEVICE to $MOUNT_DIR..." >> "$LOG_FILE"

if sudo mount "$DEVICE" "$MOUNT_DIR"; then
    echo "[SUCCESS] USB mounted at $MOUNT_DIR"
    echo "[SUCCESS] USB mounted at $MOUNT_DIR" >> "$LOG_FILE"
else
    echo "[ERROR] Failed to mount $DEVICE"
    echo "[ERROR] Failed to mount $DEVICE" >> "$LOG_FILE"
    exit 1
fi

# Desktop Notification (non-sudo)
"$SCRIPT_DIR/notify_popup.sh" "$DEVICE" "$MOUNT_DIR" &

# Email Notification
"$SCRIPT_DIR/send_email.sh" "$DEVICE" "$MOUNT_DIR" "MOUNTED"

# Logging
"$SCRIPT_DIR/log_event.sh" "$DEVICE" "$MOUNT_DIR" "MOUNTED"

# USB Antivirus Scan
"$SCRIPT_DIR/scan_usb.sh" "$DEVICE" "$MOUNT_DIR"
