<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-img
          alt="Life Game Logo"
          class="shrink mr-2"
          contain
          src="./assets/fish_manbou_mola_yoko.png"
          transition="scale-transition"
          width="70"
        />

        <v-img
          alt="Life Game Name"
          class="shrink mt-0 hidden-sm-and-down"
          contain
          min-width="100"
          src="./assets/image0.png"
          width="200"
        />
      </div>

      <v-spacer></v-spacer>

      <v-img
        alt="fish image"
        class="shrink"
        contain
        min-width="100"
        width="200"
        max-height="200"
        src="https://1.bp.blogspot.com/-_ktEzP4edpw/XrOcrhmO6mI/AAAAAAABYxc/zOEnvzBN15EIib8hIDGG7N_iRvJ73sQqgCNcBGAsYHQ/s1600/silhouette_fish_top_curve.png"
        style="transform: rotate(90deg);"
      />
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",

  data: () => ({
    //
  }),
};
</script>
