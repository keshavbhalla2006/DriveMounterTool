#!/bin/bash

LOG_FILE="../logs/mountify.log"
REPORT_FILE="../logs/test_report.txt"

if [ ! -f "$LOG_FILE" ]; then
    echo "[ERROR] Log file not found: $LOG_FILE"
    exit 1
fi

echo "====== Mountify Test Report ======" > "$REPORT_FILE"
echo "Generated on: $(date)" >> "$REPORT_FILE"
echo "==================================" >> "$REPORT_FILE"

grep -E "MOUNTED|UNMOUNTED|ERROR" "$LOG_FILE" | while read -r line; do
    echo "$line" >> "$REPORT_FILE"
done

echo "----------------------------------" >> "$REPORT_FILE"
echo "Total Events: $(grep -cE 'MOUNTED|UNMOUNTED' "$LOG_FILE")" >> "$REPORT_FILE"
echo "Errors Found: $(grep -c 'ERROR' "$LOG_FILE")" >> "$REPORT_FILE"

echo "[INFO] Test report generated at $REPORT_FILE"
