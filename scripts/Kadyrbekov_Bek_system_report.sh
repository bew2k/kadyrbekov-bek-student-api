#!/bin/bash
# System report script — Bek Kadyrbekov, IT2-2306, ID 35534
# Выводит информацию на экран и дописывает её в личный лог-файл.

STUDENT_FULL_NAME="Bek Kadyrbekov"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_FILE="${SCRIPT_DIR}/../logs/Kadyrbekov_Bek_system_report.log"

{
    echo "=============================================="
    echo " SYSTEM REPORT"
    echo "=============================================="
    echo "Student:          ${STUDENT_FULL_NAME}"
    echo "Date and time:    $(date '+%Y-%m-%d %H:%M:%S %Z')"
    echo "Current user:     $(whoami)"
    echo "Operating system: $(grep PRETTY_NAME /etc/os-release 2>/dev/null | cut -d'"' -f2)"
    echo "Kernel version:   $(uname -r)"
    echo
    echo "--- CPU ---"
    lscpu 2>/dev/null | grep -E 'Model name|^CPU\(s\)' || grep -m1 'model name' /proc/cpuinfo
    echo
    echo "--- Memory ---"
    free -h
    echo
    echo "--- Disk usage ---"
    df -h /
    echo
    echo "--- Available disk space ---"
    df -h --output=source,avail / | tail -n 1
    echo
} | tee -a "${LOG_FILE}"
