{ pkgs ? import <nixpkgs> {} }:

with pkgs;
mkShell {
  nativeBuildInputs = [
    direnv
    docker
    sbt
    scala_2_12
    scalafmt
  ];
}