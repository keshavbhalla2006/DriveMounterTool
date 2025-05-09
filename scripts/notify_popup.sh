#!/bin/bash

DEVICE="$1"
MOUNT_POINT="$2"

# Set default values if arguments not provided
DEVICE=${DEVICE:-"Unknown Device"}
MOUNT_POINT=${MOUNT_POINT:-"/mnt/usb"}

# Send desktop notification
notify-send "USB Drive Mounted" "$DEVICE mounted at $MOUNT_POINT" --icon=drive-removable-media
