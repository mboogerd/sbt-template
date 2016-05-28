# SBT Template

This is my personal SBT template to spawn new Scala projects configured with some dependencies that I typically use.

In order to use this project with a one-liner, you can use a small bash script: https://github.com/mboogerd/scripts/blob/master/scala-new-project.sh, which you can get via CLI from your preferred projects location:

```bash
$ wget https://raw.githubusercontent.com/mboogerd/scripts/master/scala-new-project.sh
$ ./scala-new-project.sh -p [your project name]
```

Additional features:
- Add `-g` to create a github repo
- Add `-b` to base your project of another branch of the template repository
- Use `-l` to list alternative templating branches

