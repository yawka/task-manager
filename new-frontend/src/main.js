import { createApp } from 'vue'
import App from './App.vue'
import { loadFonts } from './plugins/webfontloader'

loadFonts()

import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import router from '@/router/router';
import VueAxios from "vue-axios";
import axios from "axios";
import { registerLicense } from "@syncfusion/ej2-base";

const vuetify = createVuetify({
    components,
    directives,
});
registerLicense(
    "Ngo9BigBOggjHTQxAR8/V1NAaF5cWWRCf1FpRmJGdld5fUVHYVZUTXxaS00DNHVRdkdnWX5cc3ZWQ2hYUEx1XEc="
);

const app = createApp(App);
app.use(vuetify).use(router).use(VueAxios, axios)
  .mount('#app')

