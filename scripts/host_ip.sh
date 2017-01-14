# Inspired by
# http://stackoverflow.com/questions/13322485/how-to-i-get-the-primary-ip-address-of-the-local-machine-on-linux-and-os-x
# http://stackoverflow.com/questions/4247932/how-to-parse-netstat-command-in-order-to-get-process-name-and-pid-from-it

# Acquire the name of the default network interface
NET_IF=`netstat -rn | grep default | awk '{print $6}' | sed "s/\// /g"`

# Acquire the ip address of the default interface
NET_IP=`ifconfig ${NET_IF} | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1'`

# Export this as an environment variable
export HOST_IP=$NET_IP