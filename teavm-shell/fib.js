function fib(n) {
    var a = 0;
    var b = 1;
    for (var i = 0; i < n; ++i) {
        var c = a + b;
        a = b;
        b = c;
    }
    return c;
}

for (var i = 0; i < 100; ++i) {
    var start = time();
    var r = 0;
    for (var j = 0; j < 100000; ++j) {
        r += fib(j % 30) % 10;
    }
    log(time() - start);
}
