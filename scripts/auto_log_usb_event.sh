#!/bin/bash

DEVICE="$1"
LOGFILE="/home/keshav/Mountify_Project/logs/mountify.log"

echo "[INFO] Logging $DEVICE as MOUNTED" | tee -a "$LOGFILE"

# Dummy logging action
echo "[INFO] Scanning device: $DEVICE" | tee -a "$LOGFILE"

# Unmount logic
if mount | grep -q "$DEVICE"; then
    umount "$DEVICE" 2> /tmp/unmount_error.log
    if [ $? -eq 0 ]; then
        echo "[INFO] Successfully unmounted $DEVICE" | tee -a "$LOGFILE"
    else
        ERROR_MSG=$(< /tmp/unmount_error.log)
        if [[ "$ERROR_MSG" == *"target is busy"* ]]; then
            echo "[WARN] Device $DEVICE is busy, skipping unmount." | tee -a "$LOGFILE"
        else
            echo "[ERROR] Unmount failed: $ERROR_MSG" | tee -a "$LOGFILE"
        fi
    fi
else
    echo "[INFO] $DEVICE is not mounted or already unmounted." | tee -a "$LOGFILE"
fi
