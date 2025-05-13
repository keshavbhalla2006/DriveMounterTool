#!/bin/bash

DEVICE_PATH=$1  # e.g., /dev/sdc1
MOUNT_POINT=$2  # e.g., /mnt/usb
LOG_FILE="/home/keshav/Mountify_Project/logs/mountify.log"

echo "[INFO] Starting antivirus scan on USB mounted at: $MOUNT_POINT"
echo "[INFO] Starting antivirus scan on $DEVICE_PATH mounted at $MOUNT_POINT" >> "$LOG_FILE"

if [ -z "$MOUNT_POINT" ] || [ ! -d "$MOUNT_POINT" ]; then
    echo "[ERROR] Invalid or missing mount point."
    echo "[ERROR] Invalid or missing mount point for $DEVICE_PATH" >> "$LOG_FILE"
    exit 1
fi

# Check if ClamAV is installed
if ! command -v clamscan &> /dev/null; then
    echo "[WARNING] ClamAV not installed. Antivirus scan skipped."
    echo "[WARNING] ClamAV not installed. Antivirus scan skipped for $DEVICE_PATH" >> "$LOG_FILE"
    exit 1
fi

# Perform scan and save report to USB
SCAN_REPORT="$MOUNT_POINT/scan_report.txt"
clamscan -r "$MOUNT_POINT" > "$SCAN_REPORT"

if [ $? -eq 0 ]; then
    echo "[SUCCESS] No threats found. Scan report saved to $SCAN_REPORT"
    echo "[SUCCESS] No threats found on $DEVICE_PATH. Scan report saved to $SCAN_REPORT" >> "$LOG_FILE"
else
    echo "[WARNING] Threats detected! Check $SCAN_REPORT"
    echo "[WARNING] Threats detected on $DEVICE_PATH. See $SCAN_REPORT" >> "$LOG_FILE"
fi
