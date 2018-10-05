var foo;

function onCastSpell(creature) {
  creature.foo(function() {
    print("hello");
  })
}
