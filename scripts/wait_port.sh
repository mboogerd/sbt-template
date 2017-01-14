# Allows you to wait until a services' TCP port is up
TIMEOUT=1
wait() {
    testServiceUp="(echo > /dev/tcp/localhost/$1) >/dev/null 2>&1 && echo 1 || echo 0"

    currentState=$(eval $testServiceUp)
    while [[ $currentState -lt 1 ]]
    do
      echo "$2 appears down, checking again in $TIMEOUT seconds"
      sleep $TIMEOUT
      currentState=$(eval $testDBUp)
    done

    echo "$2 is listening on port $1"
}