#!/bin/bash

DEVICE="$1"
MOUNT_POINT="$2"
STATUS="$3"

TO="keshavbhalla2006@gmail.com"
FROM="keshavbhalla2006@gmail.com"
SUBJECT="USB Device $STATUS"
BODY="Device $DEVICE has been $STATUS at $MOUNT_POINT."

# Use a here-doc to construct the full email
{
  echo "From: $FROM"
  echo "To: $TO"
  echo "Subject: $SUBJECT"
  echo ""
  echo "$BODY"
} | msmtp "$TO"

echo "[INFO] Email notification sent for $DEVICE ($STATUS)"
