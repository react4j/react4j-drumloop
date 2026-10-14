desc 'Compile the cache browser tests into target/cache_test/cache_test'
task 'cache:test' => ['compile'] do
  project = Buildr.project('react4j-drumloop')
  sources = project.compile.sources.map { |path| file(path) } + [file(project._('src/test/browser'))]
  Buildr::GWT.gwtc_main(
    ['react4j.drumloop.ReactCacheTest'],
    project.compile.dependencies + sources,
    project._(:target, :cache_test),
    project._(:target, :cache_test_unit_cache),
    :version => Buildr.artifacts(:gwt_user).first.version,
    :style => 'PRETTY',
    :java_args => %w(-Xmx1024M),
    :gwtc_args => %w(-checkAssertions))
end
