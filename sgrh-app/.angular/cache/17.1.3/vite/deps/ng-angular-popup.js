import {
  CommonModule,
  NgClass,
  NgIf,
  NgStyle
} from "./chunk-RY6ML2HB.js";
import {
  Component,
  Injectable,
  Input,
  NgModule,
  setClassMetadata,
  ɵɵadvance,
  ɵɵclassProp,
  ɵɵdefineComponent,
  ɵɵdefineInjectable,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵgetCurrentView,
  ɵɵlistener,
  ɵɵnextContext,
  ɵɵproperty,
  ɵɵresetView,
  ɵɵrestoreView,
  ɵɵtemplate,
  ɵɵtext,
  ɵɵtextInterpolate
} from "./chunk-X4I554V6.js";
import {
  Subject
} from "./chunk-T7RKEGOE.js";
import "./chunk-ASLTLD6L.js";

// node_modules/ng-angular-popup/fesm2022/ng-angular-popup.mjs
function NgToastComponent_button_9_Template(rf, ctx) {
  if (rf & 1) {
    const _r2 = ɵɵgetCurrentView();
    ɵɵelementStart(0, "button", 6);
    ɵɵlistener("click", function NgToastComponent_button_9_Template_button_click_0_listener() {
      ɵɵrestoreView(_r2);
      const ctx_r1 = ɵɵnextContext();
      return ɵɵresetView(ctx_r1.closeToast());
    });
    ɵɵtext(1, "×");
    ɵɵelementEnd();
  }
}
var Position;
(function(Position2) {
  Position2["topRight"] = "tr";
  Position2["topLeft"] = "tl";
  Position2["topCenter"] = "tc";
  Position2["bottomRight"] = "br";
  Position2["bottomLeft"] = "bl";
  Position2["botomCenter"] = "bc";
})(Position || (Position = {}));
var Message;
(function(Message2) {
  Message2["error"] = "error";
  Message2["success"] = "success";
  Message2["warning"] = "warning";
  Message2["info"] = "info";
})(Message || (Message = {}));
var NgToastService = class _NgToastService {
  toastDetails$ = new Subject();
  constructor() {
  }
  getToastDetails() {
    return this.toastDetails$.asObservable();
  }
  success(message) {
    this.toastDetails$.next({
      type: Message.success,
      detail: message.detail,
      position: message.position,
      summary: message.summary,
      duration: message.duration,
      sticky: message.sticky
    });
  }
  error(message) {
    this.toastDetails$.next({
      type: Message.error,
      detail: message.detail,
      position: message.position,
      summary: message.summary,
      duration: message.duration,
      sticky: message.sticky
    });
  }
  info(message) {
    this.toastDetails$.next({
      type: Message.info,
      detail: message.detail,
      position: message.position,
      summary: message.summary,
      duration: message.duration,
      sticky: message.sticky
    });
  }
  warning(message) {
    this.toastDetails$.next({
      type: Message.warning,
      detail: message.detail,
      summary: message.summary,
      position: message.position,
      duration: message.duration,
      sticky: message.sticky
    });
  }
  static ɵfac = function NgToastService_Factory(t) {
    return new (t || _NgToastService)();
  };
  static ɵprov = ɵɵdefineInjectable({
    token: _NgToastService,
    factory: _NgToastService.ɵfac,
    providedIn: "root"
  });
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(NgToastService, [{
    type: Injectable,
    args: [{
      providedIn: "root"
    }]
  }], function() {
    return [];
  }, null);
})();
var NgToastComponent = class _NgToastComponent {
  toastService;
  show = false;
  style = {};
  toastDetails;
  timeoutRef;
  constructor(toastService) {
    this.toastService = toastService;
  }
  ngOnInit() {
    this.toastService.getToastDetails().subscribe((val) => {
      this.show = true;
      clearTimeout(this.timeoutRef);
      this.toastDetails = val;
      if (this.toastDetails.duration) {
        this.timeoutRef = setTimeout(() => {
          this.show = false;
        }, this.toastDetails.duration);
      }
      if (this.toastDetails.sticky) {
        clearTimeout(this.timeoutRef);
        this.show = true;
      }
      if (!this.toastDetails.duration && !this.toastDetails.sticky) {
        this.timeoutRef = setTimeout(() => {
          this.show = false;
        }, 4e3);
      }
    });
  }
  closeToast() {
    clearTimeout(this.timeoutRef);
    this.show = false;
  }
  loadPosition() {
    let postionClass = "";
    if (this.toastDetails?.position !== void 0) {
      switch (this.toastDetails.position) {
        case "topRight":
          postionClass = "toast-top-right";
          break;
        case "topLeft":
          postionClass = "toast-top-left";
          break;
        case "bottomRight":
          postionClass = "toast-bottom-right";
          break;
        case "bottomLeft":
          postionClass = "toast-bottom-left";
          break;
        case "topCenter":
          postionClass = "toast-top-center";
          break;
        case "botomCenter":
          postionClass = "toast-bottom-center";
          break;
        default:
          postionClass = "toast-top-right";
      }
    } else {
      postionClass = "toast-top-right";
    }
    return postionClass;
  }
  loadMessageType() {
    let typeClass = "";
    if (this.toastDetails?.type !== void 0) {
      switch (this.toastDetails.type) {
        case "success":
          typeClass = Message.success;
          break;
        case "error":
          typeClass = Message.error;
          break;
        case "warning":
          typeClass = Message.warning;
          break;
        case "info":
          typeClass = Message.info;
          break;
        default:
          typeClass = Message.success;
      }
    } else {
      typeClass = Message.success;
    }
    return typeClass;
  }
  loadIconType() {
    let iconClass = "";
    if (this.toastDetails?.type !== void 0) {
      switch (this.toastDetails.type) {
        case "success":
          iconClass = "fa-check-circle";
          break;
        case "error":
          iconClass = "fa-times-circle";
          break;
        case "warning":
          iconClass = "fa-exclamation-circle";
          break;
        case "info":
          iconClass = "fa-info-circle";
          break;
        default:
          iconClass = "fa-check-circle";
      }
    } else {
      iconClass = "fa-check-circle";
    }
    return iconClass;
  }
  static ɵfac = function NgToastComponent_Factory(t) {
    return new (t || _NgToastComponent)(ɵɵdirectiveInject(NgToastService));
  };
  static ɵcmp = ɵɵdefineComponent({
    type: _NgToastComponent,
    selectors: [["ng-toast"]],
    inputs: {
      style: "style"
    },
    decls: 10,
    vars: 9,
    consts: [[1, "toast-container", 3, "ngClass"], [1, "ng-toast", 3, "ngStyle", "ngClass"], [1, "container-1"], [1, "fa", 3, "ngClass"], [1, "container-2"], ["id", "close", 3, "click", 4, "ngIf"], ["id", "close", 3, "click"]],
    template: function NgToastComponent_Template(rf, ctx) {
      if (rf & 1) {
        ɵɵelementStart(0, "div", 0)(1, "div", 1)(2, "div", 2);
        ɵɵelement(3, "i", 3);
        ɵɵelementEnd();
        ɵɵelementStart(4, "div", 4)(5, "p");
        ɵɵtext(6);
        ɵɵelementEnd();
        ɵɵelementStart(7, "p");
        ɵɵtext(8);
        ɵɵelementEnd()();
        ɵɵtemplate(9, NgToastComponent_button_9_Template, 2, 0, "button", 5);
        ɵɵelementEnd()();
      }
      if (rf & 2) {
        ɵɵclassProp("active", ctx.show);
        ɵɵproperty("ngClass", ctx.loadPosition());
        ɵɵadvance();
        ɵɵproperty("ngStyle", ctx.style)("ngClass", ctx.loadMessageType());
        ɵɵadvance(2);
        ɵɵproperty("ngClass", ctx.loadIconType());
        ɵɵadvance(3);
        ɵɵtextInterpolate(ctx.toastDetails == null ? null : ctx.toastDetails.detail);
        ɵɵadvance(2);
        ɵɵtextInterpolate(ctx.toastDetails == null ? null : ctx.toastDetails.summary);
        ɵɵadvance();
        ɵɵproperty("ngIf", ctx.show);
      }
    },
    dependencies: [NgClass, NgIf, NgStyle],
    styles: ["*[_ngcontent-%COMP%]{padding:0;margin:0;box-sizing:border-box;border:none}.show-toast[_ngcontent-%COMP%]{margin:10px;background-color:#101020;color:#fff;padding:20px;border-radius:5px;cursor:pointer}.fa-times-circle[_ngcontent-%COMP%]{color:#ff355b}.fa-check-circle[_ngcontent-%COMP%]{color:#47d764}.fa-exclamation-circle[_ngcontent-%COMP%]{color:#ffc021}.fa-info-circle[_ngcontent-%COMP%]{color:#2f86eb}.container-1[_ngcontent-%COMP%]{margin:7px 0 7px 7px;display:flex}.container-1[_ngcontent-%COMP%]   i[_ngcontent-%COMP%]{font-size:30px}.container-2[_ngcontent-%COMP%]{display:flex;flex-direction:column;margin-left:4px;font-family:roboto,sans-serif}.container-2[_ngcontent-%COMP%]   p[_ngcontent-%COMP%]:first-child{display:flex;flex-direction:column;margin-left:5px;font-weight:700;font-size:.9rem;font-family:roboto,sans-serif}.container-2[_ngcontent-%COMP%]   p[_ngcontent-%COMP%]:last-child{font-size:14px;color:#656565;font-weight:400;font-family:roboto,sans-serif;margin-left:5px}#close[_ngcontent-%COMP%]{align-self:flex-start;background-color:transparent;font-size:25px;line-height:0;color:#656565;cursor:pointer}.toast-container[_ngcontent-%COMP%]{pointer-events:none;position:fixed;z-index:999999}.toast-top-center[_ngcontent-%COMP%]{top:5%;right:0;width:100%;transform:translateY(-400px);transition:.5s}.toast-top-center.active[_ngcontent-%COMP%]{transform:translate(0);transition:.5s}.toast-bottom-center[_ngcontent-%COMP%]{bottom:5%;right:0;width:100%;transform:translateY(400px);transition:.5s}.toast-bottom-center.active[_ngcontent-%COMP%]{transform:translate(0);transition:.5s}.toast-top-right[_ngcontent-%COMP%]{right:1%;top:5%;transform:translate(400px);transition:.5s}.toast-top-right.active[_ngcontent-%COMP%]{transform:translate(0);transition:.5s}.toast-bottom-right[_ngcontent-%COMP%]{right:1%;bottom:5%;transform:translate(400px);transition:.5s}.toast-bottom-right.active[_ngcontent-%COMP%]{transform:translate(0);transition:.5s}.toast-top-left[_ngcontent-%COMP%]{left:1%;top:5%;transform:translate(-400px);transition:.5s}.toast-top-left.active[_ngcontent-%COMP%]{transform:translate(0);transition:.5s}.toast-bottom-left[_ngcontent-%COMP%]{left:1%;bottom:5%;transform:translate(-400px);transition:.5s}.toast-bottom-left.active[_ngcontent-%COMP%]{transform:translate(0);transition:.5s}.toast-container.toast-top-center[_ngcontent-%COMP%]   .ng-toast[_ngcontent-%COMP%], .toast-container.toast-bottom-center[_ngcontent-%COMP%]   .ng-toast[_ngcontent-%COMP%]{width:350px;margin-left:auto;margin-right:auto}.ng-toast[_ngcontent-%COMP%]{width:350px;height:auto;font-size:1rem;padding:10px;box-shadow:0 10px 20px #4b32320d;border-radius:7px;display:grid;grid-template-columns:1.2fr 6fr .5fr;pointer-events:auto}.ng-toast.error[_ngcontent-%COMP%]{border-left:8px solid #ff355b;background-color:#ffe7e6}.ng-toast.warning[_ngcontent-%COMP%]{border-left:8px solid #ffc021;background-color:#fff2e2}.ng-toast.success[_ngcontent-%COMP%]{border-left:8px solid #47d764;background-color:#e4f8f0}.ng-toast.info[_ngcontent-%COMP%]{border-left:8px solid #2f86eb;background-color:#e9e9ff}"]
  });
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(NgToastComponent, [{
    type: Component,
    args: [{
      selector: "ng-toast",
      template: '<div class="toast-container" [ngClass]="loadPosition()" [class.active]="show">\n  <div [ngStyle]="style" class="ng-toast" [ngClass]="loadMessageType()">\n    <div class="container-1">\n      <i class="fa" [ngClass]="loadIconType()"></i>\n    </div>\n    <div class="container-2">\n      <p>{{ toastDetails?.detail }}</p>\n      <p>{{ toastDetails?.summary }}</p>\n    </div>\n    <button *ngIf="show" id="close" (click)="closeToast()">&times;</button>\n  </div>\n</div>\n',
      styles: ["*{padding:0;margin:0;box-sizing:border-box;border:none}.show-toast{margin:10px;background-color:#101020;color:#fff;padding:20px;border-radius:5px;cursor:pointer}.fa-times-circle{color:#ff355b}.fa-check-circle{color:#47d764}.fa-exclamation-circle{color:#ffc021}.fa-info-circle{color:#2f86eb}.container-1{margin:7px 0 7px 7px;display:flex}.container-1 i{font-size:30px}.container-2{display:flex;flex-direction:column;margin-left:4px;font-family:roboto,sans-serif}.container-2 p:first-child{display:flex;flex-direction:column;margin-left:5px;font-weight:700;font-size:.9rem;font-family:roboto,sans-serif}.container-2 p:last-child{font-size:14px;color:#656565;font-weight:400;font-family:roboto,sans-serif;margin-left:5px}#close{align-self:flex-start;background-color:transparent;font-size:25px;line-height:0;color:#656565;cursor:pointer}.toast-container{pointer-events:none;position:fixed;z-index:999999}.toast-top-center{top:5%;right:0;width:100%;transform:translateY(-400px);transition:.5s}.toast-top-center.active{transform:translate(0);transition:.5s}.toast-bottom-center{bottom:5%;right:0;width:100%;transform:translateY(400px);transition:.5s}.toast-bottom-center.active{transform:translate(0);transition:.5s}.toast-top-right{right:1%;top:5%;transform:translate(400px);transition:.5s}.toast-top-right.active{transform:translate(0);transition:.5s}.toast-bottom-right{right:1%;bottom:5%;transform:translate(400px);transition:.5s}.toast-bottom-right.active{transform:translate(0);transition:.5s}.toast-top-left{left:1%;top:5%;transform:translate(-400px);transition:.5s}.toast-top-left.active{transform:translate(0);transition:.5s}.toast-bottom-left{left:1%;bottom:5%;transform:translate(-400px);transition:.5s}.toast-bottom-left.active{transform:translate(0);transition:.5s}.toast-container.toast-top-center .ng-toast,.toast-container.toast-bottom-center .ng-toast{width:350px;margin-left:auto;margin-right:auto}.ng-toast{width:350px;height:auto;font-size:1rem;padding:10px;box-shadow:0 10px 20px #4b32320d;border-radius:7px;display:grid;grid-template-columns:1.2fr 6fr .5fr;pointer-events:auto}.ng-toast.error{border-left:8px solid #ff355b;background-color:#ffe7e6}.ng-toast.warning{border-left:8px solid #ffc021;background-color:#fff2e2}.ng-toast.success{border-left:8px solid #47d764;background-color:#e4f8f0}.ng-toast.info{border-left:8px solid #2f86eb;background-color:#e9e9ff}\n"]
    }]
  }], function() {
    return [{
      type: NgToastService
    }];
  }, {
    style: [{
      type: Input
    }]
  });
})();
var NgToastModule = class _NgToastModule {
  static ɵfac = function NgToastModule_Factory(t) {
    return new (t || _NgToastModule)();
  };
  static ɵmod = ɵɵdefineNgModule({
    type: _NgToastModule,
    declarations: [NgToastComponent],
    imports: [CommonModule],
    exports: [NgToastComponent]
  });
  static ɵinj = ɵɵdefineInjector({
    imports: [CommonModule]
  });
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(NgToastModule, [{
    type: NgModule,
    args: [{
      declarations: [NgToastComponent],
      imports: [CommonModule],
      exports: [NgToastComponent]
    }]
  }], null, null);
})();
export {
  Message,
  NgToastComponent,
  NgToastModule,
  NgToastService,
  Position
};
//# sourceMappingURL=ng-angular-popup.js.map
