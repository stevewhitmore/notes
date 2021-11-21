# Bluetooth Battery

There's no simple way to check the battery life of connected Bluetooth headphones in Fedora 34 Workstation for some reason. This snippet combines the useful Python script with a few commands into a single command to check the battery level of my Cowin E7 headphones. The MAC address will be exposed but I don't care much about that. What are those black hats going to do, hijack my headphones? Probably not?

--

[Bluetooth Headset Battery Level](https://github.com/TheWeirdDev/Bluetooth_Headset_Battery_Level) by [TheWeirdDev](https://github.com/TheWeirdDev). Other parts added by me.

```bash
# Assumes `bluetooth_battery.py` is chmod'd with 755 (+x) permissions
# Change "mac_address" and file path as needed

function bluetoothbattery {
    local mac_address="FC:58:FA:AC:5B:DF"

    cd ~/workspace/Bluetooth_Headset_Battery_Level || exit
    bluetoothctl disconnect "$mac_address"
    ./bluetooth_battery.py "$mac_address"
    cd -
}
```

